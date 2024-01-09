# Group 2: Creator Connect

Backend Live :rocket: on https://asdc-project-group2.onrender.com/

### Login SignUp API Docs:

Ex payload:

```
{ 

    "email": "inf1@gmail.com", 

    "password": "password1", 

    "user_type": "Influencer" 

}
```

| API Name        | URL Path                        | Type | Request Body                                                                      | Response Body                                  |
| --------------- | ------------------------------- | ---- | --------------------------------------------------------------------------------- | ---------------------------------------------- |
| Register User   | POST /api/users/register        | POST | `{"email": "user@example.com", "password": "password", "user_type": "Influencer"}` | "Registered successfully"                      |
| Login User      | POST /api/users/login           | POST | `{"email": "user@example.com", "password": "password"}`                           | "Logged in successfully"                       |
| Forgot Password | POST /api/users/forgot-password | POST | `{"email": "user@example.com"}`                                                   | "Email with reset password link has been sent" |
| Reset Password  | POST /api/users/reset-password  | POST | `{"token": "reset_token", "password": "new_password"}`                            | "Password has been reset"                      |


### Influencer API Docs:

**Example Payload for register Influencer:**

```
{ 

    "name": "John Doe", 

    "profileImage": "http://image.png", 

    "gender": "MALE", 

    "influencerName": "john.doe", 

    "influencerType": "Fashion", 

    "interestedIn": "Clothing", 

    "minRate": 1000, 

    "previousBrands": "Nike, Adidas", 

    "location": "New York", 

    "bestPosts": "https://instagram.com" 

}
```

|API Name|URL Path|Type|Request Body|Response Body|
|---|---|---|---|---|
|Register Influencer|POST /api/influencers/register/{userId}|POST|JSON object representing the Influencer|JSON object representing the registered Influencer|
|Get Influencer by ID|GET /api/influencers/{id}|GET|-|JSON object representing the Influencer|
|Update Influencer|PUT /api/influencers/{id}|PUT|JSON object representing the updated Influencer|JSON object representing the updated Influencer|
|Get All Influencers|GET /api/influencers|GET|-|JSON array representing a list of Influencers|
|Delete Influencer by ID|DELETE /api/influencers/{id}|DELETE|-|-|


### Organization API Docs:

**Example Payload for register Organization:**

```
{ 

    "orgName": "Pepsi", 

    "profileImage": "https://image.png", 

    "companyType": "Type A", 

    "size": 100, 

    "websiteLink": "http://Pepsi.com", 

    "targetInfluencerType": "Sports", 

    "location": "North America" 

}
```

|API Name|URL Path|Type|Request Body|Response Body|
|---|---|---|---|---|
|Register Organization|POST /api/organizations/register/{userId}|POST|JSON object representing the Organization|JSON object representing the registered Organization|
|Get Organization by ID|GET /api/organizations/{id}|GET|-|JSON object representing the Organization|
|Update Organization|PUT /api/organizations/{id}|PUT|JSON object representing the updated Organization|JSON object representing the updated Organization|
|Get All Organizations|GET /api/organizations|GET|-|JSON array representing a list of Organizations|
|Delete Organization by ID|DELETE /api/organizations/{id}|DELETE|-|-|



### Connection Request API Docs:

**Example Payload for create Connection Request:**
```
{
     "orgID": 1,

     "influencerID": 10,

     "requestMessage": "Hello, I would like to connect!",

     "requestStatus": "Pending" 
}
```

|API Name|URL Path|Type|Request Body|Response Body|
|---|---|---|---|---|
|Create Connection Request|POST /api/connectionReq/create|POST|JSON object representing the Connection Request|JSON object representing the created Connection Request|
|Get Connection Request by ID|GET /api/connectionReq/getByRequestID/{requestId}|GET|-|JSON object representing the Connection Request|
|Update Connection Request Status|PUT /api/connectionReq/update/{requestId}|PUT|JSON object containing the requestStatus field|String indicating the status of the updated Connection Request|
|Get Requests by Influencer ID|GET /api/connectionReq/influencer/getByID/{id}|GET|-|JSON array representing a list of Connection Requests|
|Get Requests by Organization ID|GET /api/connectionReq/organization/getByID/{id}|GET|-|JSON array representing a list of Connection Requests|
|Get Requests by Organization ID & Status|GET /api/connectionReq/organization/{orgID}/status/{status}|GET|-|JSON array representing a list of Connection Requests|
|Get All Connection Requests|GET /api/connectionReq/getAll|GET|-|JSON array representing a list of Connection Requests|
|Delete Connection Request by ID|DELETE /api/connectionReq/delete/{id}|DELETE|-|-|
|Update Connection Request Message|PUT /api/connectionReq/updateMessage/{id}|PUT|String containing the updated message|JSON object representing the updated Connection Request|
