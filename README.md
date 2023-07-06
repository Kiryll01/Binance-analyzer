# Binance-analyzer
pet-project : binance stats analyzer 

This service retrieves data from Binance api and calculate some stats. Serice uses mysql and redis as databases. 
Redis is used to save user sessions and some calculations, after session disconnect data is going to mysql.
The whole scenario is something like this: user makes sign_up request to add his information to db. After that he authenticates via sign_in request and obtains Authentication header with httpBasic token.
App uses roles to separate access areas. By default user has ROLE_RAW, to change it, he can make SET_PROPERTIES_INFORMATION request and get ROLE_FETCH_STATS if the request contains valid properties.
Then he can establish webSocket connection and subscribe to fetch_MA_stats or fetch_ticker_stats links to receive calculated stats. Scheduled methods will send data to every user depending on their properties found in redis. When user decides to close connection his redis data will be saved in mysql.

In future i will add some new features to the project like: calculating bollinger bands, giving advices to buy or sell and sending notifications to email.

Thats all by now!
