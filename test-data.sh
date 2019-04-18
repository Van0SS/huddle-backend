curl -X POST "http://127.0.0.1:8084/prefs" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"activities\": {    \"CITY_BIKE\": {      \"activity\": \"CITY_BIKE\",      \"details\": \"ride a bike blah blah\",            \"sameGender\": false,      \"times\": [        \"BEFORE_WORK\"      ]    }  },  \"bio\": \"SOme into aobut em\",    \"gender\": \"MALE\",  \"userId\": \"rwilson@domain.com\"}"

curl -X POST "http://127.0.0.1:8084/prefs" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"activities\": {    \"CITY_BIKE\": {      \"activity\": \"CITY_BIKE\",      \"details\": \"ride a bike blah blah\",            \"sameGender\": false,      \"times\": [        \"BEFORE_WORK\"      ]    }  },  \"bio\": \"Infffffffo\",    \"gender\": \"MALE\",  \"userId\": \"rwilson2@domain.com\"}"

printf "Ask for match for rwilson, should be rwilson2\n"
curl -X GET "http://127.0.0.1:8084/match?userId=rwilson%40domain.com" -H  "accept: */*"

curl -X POST "http://127.0.0.1:8084/match/unmatch?unmatchUserId=rwilson2%40domain.com&userId=rwilson%40domain.com" -H  "accept: */*"

printf "\n\nrwilson, should be unmatched with rwilson2\n"
curl -X GET "http://127.0.0.1:8084/match?userId=rwilson%40domain.com" -H  "accept: */*"

curl -X POST "http://127.0.0.1:8084/prefs" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"activities\": {    \"GYM\": {      \"activity\": \"GYM\",      \"details\": \"some gym\",            \"sameGender\": false,      \"times\": [        \"AFTERNOON\", \"AFTER_WORK\"      ]    }, \"CITY_BIKE\": {      \"activity\": \"CITY_BIKE\",      \"details\": \"\",            \"sameGender\": true,      \"times\": [        \"AFTERNOON\", \"AFTER_WORK\"     ]    }  },  \"bio\": \"I am a cool guy\",    \"gender\": \"MALE\",  \"userId\": \"sidsharm@domain.com\"}"

curl -X POST "http://127.0.0.1:8084/prefs" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"activities\": {    \"CITY_BIKE\": {      \"activity\": \"CITY_BIKE\",      \"details\": \"ride a bike blah blah\",            \"sameGender\": true,      \"times\": [        \"LUNCHTIME\"      ]    }  },  \"bio\": \"Infffffffo\",    \"gender\": \"MALE\",  \"userId\": \"lunchguy1@domain.com\"}"

curl -X POST "http://127.0.0.1:8084/prefs" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"activities\": {    \"CITY_BIKE\": {      \"activity\": \"CITY_BIKE\",      \"details\": \"ride a bike blah blah\",            \"sameGender\": false,      \"times\": [        \"AFTER_WORK\"      ]    }  },  \"bio\": \"Infffffffo\",    \"gender\": \"FEMALE\",  \"userId\": \"bikegirl@domain.com\"}"

curl -X POST "http://127.0.0.1:8084/prefs" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"activities\": {    \"GYM\": {      \"activity\": \"GYM\",      \"details\": \"some gym\",            \"sameGender\": false,      \"times\": [        \"AFTER_WORK\"      ]    }  },  \"bio\": \"I f love gym\",    \"gender\": \"FEMALE\",  \"userId\": \"gymgirl@domain.com\"}"

printf "\n\nsidsharm + gymgirl, but not sidsharm + bikegirl nor sidsharm + lunchguy1\n"
curl -X GET "http://127.0.0.1:8084/match?userId=sidsharm%40domain.com" -H  "accept: */*"

curl -X POST "http://127.0.0.1:8084/prefs" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"activities\": {    \"CITY_BIKE\": {      \"activity\": \"CITY_BIKE\",      \"details\": \"ride a bike blah blah\",            \"sameGender\": true,      \"times\": [        \"LUNCHTIME\"      ]    }  },  \"bio\": \"Infffffffo\",    \"gender\": \"MALE\",  \"userId\": \"lunchguy2@domain.com\"}"

curl -X POST "http://127.0.0.1:8084/prefs" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"activities\": {    \"CITY_BIKE\": {      \"activity\": \"CITY_BIKE\",      \"details\": \"ride a bike blah blah\",            \"sameGender\": true,      \"times\": [        \"LUNCHTIME\"      ]    }  },  \"bio\": \"Infffffffo\",    \"gender\": \"MALE\",  \"userId\": \"lunchguy3@domain.com\"}"

curl -X POST "http://127.0.0.1:8084/prefs" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"activities\": {    \"CITY_BIKE\": {      \"activity\": \"CITY_BIKE\",      \"details\": \"ride a bike blah blah\",            \"sameGender\": true,      \"times\": [        \"LUNCHTIME\"      ]    }  },  \"bio\": \"Infffffffo\",    \"gender\": \"MALE\",  \"userId\": \"lunchguy4@domain.com\"}"

curl -X POST "http://127.0.0.1:8084/prefs" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"activities\": {    \"CITY_BIKE\": {      \"activity\": \"CITY_BIKE\",      \"details\": \"ride a bike blah blah\",            \"sameGender\": true,      \"times\": [        \"LUNCHTIME\"      ]    }  },  \"bio\": \"Infffffffo\",    \"gender\": \"MALE\",  \"userId\": \"lunchguy5@domain.com\"}"

curl -X POST "http://127.0.0.1:8084/prefs" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"activities\": {    \"CITY_BIKE\": {      \"activity\": \"CITY_BIKE\",      \"details\": \"ride a bike blah blah\",            \"sameGender\": true,      \"times\": [        \"LUNCHTIME\"      ]    }  },  \"bio\": \"Infffffffo\",    \"gender\": \"MALE\",  \"userId\": \"lunchguy6@domain.com\"}"

printf "\n\n lunchguy1 should get 23456 but no more than 3\n"
curl -X GET "http://127.0.0.1:8084/match?userId=lunchguy1%40domain.com" -H  "accept: */*"

printf "\n\n lunchguy2 should get 13456 but no more than 3\n"
curl -X GET "http://127.0.0.1:8084/match?userId=lunchguy2%40domain.com" -H  "accept: */*"

printf "\n\n lunchguy3 should get 12456 but no more than 3\n"
curl -X GET "http://127.0.0.1:8084/match?userId=lunchguy3%40domain.com" -H  "accept: */*"

printf "\n\n lunchguy4 should get 12356 but no more than 3\n"
curl -X GET "http://127.0.0.1:8084/match?userId=lunchguy4%40domain.com" -H  "accept: */*"

printf "\n\n lunchguy5 should get 12346 but no more than 3\n"
curl -X GET "http://127.0.0.1:8084/match?userId=lunchguy5%40domain.com" -H  "accept: */*"

printf "\n\n lunchguy6 should get 12345 but no more than 3\n"
curl -X GET "http://127.0.0.1:8084/match?userId=lunchguy6%40domain.com" -H  "accept: */*"

