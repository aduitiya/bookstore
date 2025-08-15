from fastapi import FastAPI, HTTPException, Header
from pydantic import BaseModel, EmailStr
from typing import Optional, Dict, List

app = FastAPI()

# In-memory stores
USERS: Dict[str, str] = {}
BOOKS: Dict[int, Dict[str, str]] = {}
NEXT_ID = 1

class Signup(BaseModel):
    email: EmailStr
    password: str

class Login(BaseModel):
    email: EmailStr
    password: str

class Book(BaseModel):
    title: str
    author: str

@app.get("/health")
def health():
    return {"status": "up"}

@app.post("/signup")
def signup(payload: Signup):
    if payload.email in USERS:
        # already exists
        raise HTTPException(status_code=400, detail="User already exists")
    USERS[payload.email] = payload.password
    return {"email": payload.email}

@app.post("/login")
def login(payload: Login):
    if USERS.get(payload.email) == payload.password:
        return {"token_type": "bearer", "access_token": f"fake-token-{payload.email}"}
    raise HTTPException(status_code=401, detail="Invalid credentials")

def _require_auth(auth: Optional[str]):
    if not auth or not auth.lower().startswith("bearer ") or len(auth.split(" ", 1)[1]) < 3:
        raise HTTPException(status_code=401, detail="Unauthorized")

@app.get("/books")
def list_books():
    return list(BOOKS.values())

@app.post("/books", status_code=201)
def create_book(book: Book, authorization: Optional[str] = Header(None)):
    _require_auth(authorization)
    global NEXT_ID
    book_id = NEXT_ID
    NEXT_ID += 1
    data = {"id": book_id, "title": book.title, "author": book.author}
    BOOKS[book_id] = data
    return data

@app.get("/books/{book_id}")
def get_book(book_id: int, authorization: Optional[str] = Header(None)):
    _require_auth(authorization)
    if book_id not in BOOKS:
        raise HTTPException(status_code=404, detail="Not found")
    return BOOKS[book_id]

@app.put("/books/{book_id}")
def update_book(book_id: int, book: Book, authorization: Optional[str] = Header(None)):
    _require_auth(authorization)
    if book_id not in BOOKS:
        raise HTTPException(status_code=404, detail="Not found")
    BOOKS[book_id]["title"] = book.title
    BOOKS[book_id]["author"] = book.author
    return BOOKS[book_id]

@app.delete("/books/{book_id}", status_code=204)
def delete_book(book_id: int, authorization: Optional[str] = Header(None)):
    _require_auth(authorization)
    if book_id not in BOOKS:
        raise HTTPException(status_code=404, detail="Not found")
    del BOOKS[book_id]
    return {}
