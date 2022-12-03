### curl samples

#### get All Meals
`curl http://localhost:8080/topjava/rest/meals`

#### get Meal for id - 100004
`curl http://localhost:8080/topjava/rest/meals/100004`

#### filter Meals
`curl "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=09:00:00&endDate=2020-01-31&endTime=11:00:00"`

#### delete Meal for id - 100007
`curl -X DELETE http://localhost:8080/topjava/rest/meals/100007`

#### create Meal
`curl -X POST -d '{"dateTime":"2022-02-01T12:00","description":"Created lunch","calories":650}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/meals`

#### update Meals with id - 100008
`curl -X PUT -d '{"dateTime":"2020-01-31T07:00", "description":"Updated dinner", "calories":720}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/meals/100008`

#### get All Users
`curl http://localhost:8080/topjava/rest/admin/users`

#### get User for id - 100001
`curl http://localhost:8080/topjava/rest/admin/users/100001`