# Copyright 2015 Google Inc.
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

import os

import pytest

import requests

from retrying import retry


@pytest.mark.e2e
def test_end_to_end():
    """Tests designed to be run against live environments.

    Unlike the integration tests in the other packages, these tests are
    designed to be run against fully-functional live environments.

    To run locally, start both main.py and psq_worker main.books_queue and
    run this file.

    It can be run against a live environment by setting the E2E_URL
    environment variables before running the tests:

        E2E_URL=http://your-app-id.appspot.com \
        nosetests tests/test_end_to_end.py
    """

    base_url = os.environ.get('E2E_URL', 'http://localhost:8080')

    # Use retry because it will take some indeterminate time for the pub/sub
    # message to be processed.
    @retry(wait_exponential_multiplier=2000, stop_max_attempt_number=3)
    def test_request():
        # Check that the book's information was updated.
        print("connecting to {}...".format(base_url))
        response = requests.get(base_url)
        assert response.status_code == 200
        assert response.text == "Hello World"

    # Run tests
    try:
        test_request()
    finally:
        # No cleanup necessary
        pass
