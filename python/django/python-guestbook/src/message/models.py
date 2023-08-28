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

from django.db import models

# Create your models here.
class Message(models.Model):
    author = models.CharField(max_length=250)
    slug = models.SlugField(max_length=250)
    date = models.DateTimeField(auto_now_add=True)
    content = models.TextField(blank=True)

    class Meta:
        ordering = ('author',)
        verbose_name = 'message'
        verbose_name_plural = 'messages'

    def __str__(self):
        return '{}'.format(self.content)
