// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package main

import (
	"context"
	"fmt"
	"time"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type mongodb struct {
	conn *mongo.Client
	list []guestbookEntry
}

func (m *mongodb) entries() ([]guestbookEntry, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	col := m.conn.Database("guestbook").Collection("entries")
	cur, err := col.Find(ctx, bson.D{}, &options.FindOptions{
		Sort: bson.D{{"_id", -1}},
	})
	if err != nil {
		return nil, fmt.Errorf("mongodb.Find failed: %+v", err)
	}
	defer cur.Close(ctx)

	var out []guestbookEntry
	for cur.Next(ctx) {
		var v guestbookEntry
		if err := cur.Decode(&v); err != nil {
			return nil, fmt.Errorf("decoding mongodb doc failed: %+v", err)
		}
		out = append(out, v)
	}
	if err := cur.Err(); err != nil {
		return nil, fmt.Errorf("failed to iterate on mongodb cursor: %+v", err)
	}
	return out, nil
}

func (m *mongodb) addEntry(e guestbookEntry) error {
	m.list = append([]guestbookEntry{e}, m.list...)

	ctx, cancel := context.WithTimeout(context.Background(), time.Second*3)
	defer cancel()

	col := m.conn.Database("guestbook").Collection("entries")
	if _, err := col.InsertOne(ctx, e); err != nil {
		return fmt.Errorf("mongodb.InsertOne failed: %+v", err)
	}
	return nil
}
