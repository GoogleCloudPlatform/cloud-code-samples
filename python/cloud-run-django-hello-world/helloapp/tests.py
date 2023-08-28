# Copyright 2023 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from django.test import SimpleTestCase, Client


class ViewTests(SimpleTestCase):
    def test_home_page_response(self):
        client = Client()
        resp = client.get('/')
        self.assertEqual(resp.status_code, 200)
        self.assertContains(resp, 'Hello, world!')
        self.assertContains(resp, 'About')

    def test_about_page_response(self):
        client = Client()
        resp = client.get('/about/')
        self.assertEqual(resp.status_code, 200)
        self.assertContains(resp, 'This is an example')
        self.assertContains(resp, 'Home')
