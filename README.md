# Library Management API

## Objective
This project aims to act as a `RESTFUL API` for a `frontend` framework for a browser or mobile application. The project is about a library management system where I user can borrow a book for reading. The books are stored with their number of available copies. The borrowing is stored as a record where it saves when the book is borrowed and the due date. It also stores when the book is returned. The book also maintains the number of borrowed copies that haven't returned yet.

## UML of the stored data and their relations
![Stored Data UML](Data%20UML.png)


## API Endpoints

### GET `/api/books`

Returns all stored books

```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 12 Aug 2024 05:55:21 GMT
Connection: close

[
  {
    "id": 1,
    "isbn": "978-0735211292",
    "title": "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
    "authorName": "James Clear",
    "publicationYear": 2018,
    "availableCopiesCount": 18,
    "borrowedCopiesCount": 0
  },
  {
    "id": 2,
    "isbn": "978-0385348638",
    "title": "Better Than Before: What I Learned About Making and Breaking Habits to Sleep More, Quit Sugar, Procrastinate Less, and Generally Build a Happier Life",
    "authorName": "Gretchen Rubin",
    "publicationYear": 2015,
    "availableCopiesCount": 10,
    "borrowedCopiesCount": 0
  }
]
```

### GET `/api/books/{id}`
#### `GET /api/books/1`
returns the books with the given `id`.

```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 12 Aug 2024 05:58:24 GMT
Connection: close

{
  "id": 1,
  "isbn": "978-0735211292",
  "title": "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
  "authorName": "James Clear",
  "publicationYear": 2018,
  "availableCopiesCount": 18,
  "borrowedCopiesCount": 0
}
```

#### `GET /api/books/3`
If the `id` doesn't exist, it returns `not found`.

```
HTTP/1.1 404 
Content-Type: text/plain;charset=UTF-8
Content-Length: 29
Date: Mon, 12 Aug 2024 06:00:16 GMT
Connection: close

No book with Id `3` is found.
```

### POST `/api/books`
Adds a new book
```
POST /api/books HTTP/1.1
Content-Type: application/json

{
  "isbn": "978-0735211292",
  "title": "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
  "authorName": "James Clear",
  "publicationYear": 2018,
  "availableCopiesCount": 18
}
```
Response
```
HTTP/1.1 201 
Location: /api/books/1
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 12 Aug 2024 06:03:13 GMT
Connection: close

{
  "id": 1,
  "isbn": "978-0735211292",
  "title": "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
  "authorName": "James Clear",
  "publicationYear": 2018,
  "availableCopiesCount": 18,
  "borrowedCopiesCount": 0
}
```

#### Note
Adding a new book requires all the properties shown above and `isbn` has to follow the standard and unique accross all added books and `publicationYear` has to be starting from `1000` and available copies count cannot be negative. These constraints holds as well for updating a book.

The `borrowedCopiesCount` property is a view only property; it can't be inserted nor updated from the endpoint.

### PUT `/api/books/{id}`
```
PUT /api/books/1 HTTP/1.1
Content-Type: application/json

{
  "isbn": "978-0735211292",
  "title": "Atomic Habits",
  "authorName": "James Clear",
  "publicationYear": 2018,
  "availableCopiesCount": 20
}
```
Response
```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 12 Aug 2024 06:12:13 GMT
Connection: close

{
  "id": 1,
  "isbn": "978-0735211292",
  "title": "Atomic Habits",
  "authorName": "James Clear",
  "publicationYear": 2018,
  "availableCopiesCount": 20,
  "borrowedCopiesCount": 0
}
```
If No book exisits with the specified `id` a bad request is returned.
```
PUT /api/books/5 HTTP/1.1
Content-Type: application/json

{
    "isbn": "978-0385348638",
    "title": "Better Than Before: What I Learned About Making and Breaking Habits to Sleep More, Quit Sugar, Procrastinate Less, and Generally Build a Happier Life",
    "authorName": "Gretchen Rubin",
    "publicationYear": 2015,
    "availableCopiesCount": 10
}
```
Response
```
HTTP/1.1 400 
Content-Type: text/plain;charset=UTF-8
Content-Length: 27
Date: Mon, 12 Aug 2024 06:17:20 GMT
Connection: close

No book with Id 5 is found.
```

#### Note
the previously laid out constraints in adding also holds when updating.

### DELETE `api/books/{id}`
```
DELETE /api/books/1 HTTP/1.1
```
Response
```
HTTP/1.1 200 
Content-Length: 0
Date: Mon, 12 Aug 2024 06:19:39 GMT
Connection: close


```
If no book with the specified `id` exists, results in `not found`.
```
DELETE /api/books/5 HTTP/1.1
```
Response
```
HTTP/1.1 404 
Content-Length: 0
Date: Mon, 12 Aug 2024 06:22:20 GMT
Connection: close

```