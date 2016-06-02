# BlackJack REST service

### BlackJack API:

#### Objects:
- ##### User:

- ##### Table:

- ##### Urls:
    - URL: /account
      Type: PUT
      BODY: JSON
      ```
      {"nickName": "your_nickname",
       "login": "your_login",
       "password": "pass"}
      ```
      Effect: Create new user with start balance $10_000 in database
      RESPONSE: JSON
      ```
       {"status": 0,     
       "message": "status message"}
      ```

    - URL: /account/{nickName}
      Type: GET
      BODY: NONE
      Effect: User info like login, nickname, balance
      RESPONSE: JSON
      ```
      {"nickName": "your_nickname",
       "login": "your_login",
       "balance": 10000}
      ```

    - URL: /account/{nickName}
      Type: DELETE
      BODY: NONE
      Effect: Delete user from database
      RESPONSE:  JSON
      ```json
       {"status": 0,     
       "message": "status message"}
      ```