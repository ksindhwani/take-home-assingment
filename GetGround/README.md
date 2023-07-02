## Summary

- This task should not take more than 5 hours.
- You can use the provided skeleton project structure (optional). If you are using the following supported languages:
  - `golang`
  - `java`

## Requirements

- Please use MySQL version 5.7 as a database
- We are expecting well tested and structured code
- Good documentation to get us started and understand your implementation
- We will run `make postman-public-test` to ensure the submission pass the basic correctness tests

**NOTE: Please DO NOT upload this task or your implementation to a public repository. If you must use version control, consider using a private repository instead.**

## Task Overview

Hopefully this tech task allows you to strut your stuff as much as you decide to!

We'd like to implement a guestlist service for the GetGround year end party!
We haven't decide on the venue yet so the number of tables and the capacity are subject to change.

When the party begins, guests will arrive with an entourage. This party may not be the size indicated on the guest list. 
However, if it is expected that the guest's table can accommodate the extra people, then the whole party should be let in. Otherwise, they will be turned away.
Guests will also leave throughout the course of the party. Note that when a guest leaves, their accompanying guests will leave with them.

At any point in the party, we should be able to know:
- Our guests at the party
- How many empty seats there are

Please suggest possible improvements to the api-guide and your submission.

## Submission

Please use git for version control and bundle your submission with `make bundle`. Rename the bundle to `[YOUR_NAME].bundle` before submission. Please do NOT upload the submission to a public repository! 

## API guide

### Add table

```
POST /tables
body: 
{
    "capacity": 10
}
response: 
{
    "id": 2,
    "capacity": 10
}
```

### Add a guest to the guestlist

If there is insufficient space at the specified table, then an error should be thrown.

```
POST /guest_list/name
body: 
{
    "table": int,
    "accompanying_guests": int
}
response: 
{
    "name": "string"
}
```

### Get the guest list

```
GET /guest_list
response: 
{
    "guests": [
        {
            "name": "string",
            "table": int,
            "accompanying_guests": int
        }, ...
    ]
}
```

### Guest Arrives

A guest may arrive with an entourage that is not the size indicated at the guest list.
If the table is expected to have space for the extras, allow them to come. Otherwise, this method should throw an error.

```
PUT /guests/name
body:
{
    "accompanying_guests": int
}
response:
{
    "name": "string"
}
```

### Guest Leaves

When a guest leaves, all their accompanying guests leave as well.

```
DELETE /guests/name
response code: 204
```

### Get arrived guests

```
GET /guests
response: 
{
    "guests": [
        {
            "name": "string",
            "accompanying_guests": int,
            "time_arrived": "string"
        }
    ]
}
```

### Count number of empty seats

```
GET /seats_empty
response:
{
    "seats_empty": int
}
```

## Correctness test
We have included a postman collection for basic correctness test. `postman/GetGroundTechTask.postman_collection.json`
Note that, this does not replace unit-testing or integration testing. We will have private test cases when we evaluate the submission. 
