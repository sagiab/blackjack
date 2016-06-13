# BlackJack REST service

### BlackJack API:

#### Urls:
- ##### Account:

    ```json
    
        URL: /account
        TYPE: POST
        BODY: JSON
            {
                "login": "your_login",
                "nickName": "your_nickname",
                "password": "pass"
            }
        EFFECT: Create new user with start balance $10_000 in database
        RESPONSE: JSON
            {   
                "status": 0 or -1,     
                "message": "status message"
            }
            
    ```

    ```json

        URL: /account/login
        TYPE: POST
        BODY: JSON
            {
                "login": "login",
                "password": "password"
            }
        EFFECT: User info like login, nickname, balance
        RESPONSE: JSON
            {
                "nickName": "NickName",
                "balance": 10000
            }
            
    ```

    ```json

        URL: /account/{login}
        TYPE: DELETE
        BODY: NONE
        EFFECT: Delete user from database
        RESPONSE:  JSON
            {
                "status": 0 or -1,     
                "message": "status message"
            }
       
    ```

- ##### Table:

    ```json
        URL: /account/{login}/select-table
        TYPE: GET
        BODY: NONE
        EFFECT: Gets the available tables types
        RESPONSE:  JSON
            [
                {
                    "id": 1,
                    "minBetSize": 10,
                    "maxBetSize": 100
                },
                ...
            ]
       
    ```

    ```json

        URL: /account/{login}/table
        TYPE: POST
        BODY: 
            {
                "tabletype": 1
            }
        EFFECT: Create new table by tabletype for user by login
        RESPONSE:  JSON
            {
                "tableId": 7
            }
       
    ```

    ```json

        URL: /account/{login}/table/{tableID}
        TYPE: GET
        BODY: NONE
        EFFECT: Gets available commands for this state of game or error message if the game not started.
        RESPONSE:  JSON
            [
                {
                    "type": "HIT",
                    "available": true
                },
                {
                    "type": "BET",
                    "available": false
                },
                {
                    "type": "STAND",
                    "available": true
                },
                {
                    "type": "EXIT",
                    "available": true
                }
            ]
       
    ```

    - Commands:

        ```json
    
            URL: /account/{login}/table/{tableID}/bet/{betSize}
            TYPE: GET
            BODY: NONE
            EFFECT: Sends bet command to server with bet size from url
            RESPONSE:  JSON with result
                
           
        ```

        ```json

            URL: /account/{login}/table/{tableID}/hit
            TYPE: GET
            BODY: NONE
            EFFECT: Sends hit command to server
            RESPONSE:  JSON with dealed player and one of dealer cards


        ```

        ```json

            URL: /account/{login}/table/{tableID}/stand
            TYPE: GET
            BODY: NONE
            EFFECT: Sends stand command to server
            RESPONSE:  JSON with result of this game.


        ```
