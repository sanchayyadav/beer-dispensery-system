
# <p align="center">Title</p>
  
This REST APIs manages the flow of a tap beer dispenser mechanism to help those bars that allow their clients to serve themselves beer. 
Every time a client opens the tap, this API starts counting how many liters come out of thetap until is closed.
After that, the bartender could know how much their customers have spent drinking beer.
## 🧐 Features    
When you open/close a beer tap of dispensery after closing tap of dispensery you will get what amount you need to pay for beer.
## 🛠️ Tech Stack
- Spring Boot
- MySql
- JWT
- Swagger
- Maven
- JPA
- Docker


## ➤ API Reference

### Create a dispensery
```http
POST /rviewer/beer-tap-dispenser/90004725/dispenser
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `flow_volume`   | `Double` | `Yes`  

### Get dispensery by id
```http
GET /rviewer/beer-tap-dispenser/90004725/dispenser/{id}/spending
```
### Update dispensery by id
```http
PUT /rviewer/beer-tap-dispenser/90004725/dispenser/{id}/status
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `status`   | `String` | `Yes` 
| `updated_at`   | `LocalDateTime` | `Yes`

## 🙇 Author
#### Sanchay Yadav
- LinkedIn : https://www.linkedin.com/in/sanchay-yadav-96691116a/
- Github: https://github.com/sanchayyadav
        


        


       
        
        
    
        
    
