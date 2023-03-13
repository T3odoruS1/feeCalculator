# Delivery fee calculator

This API was developed as a trial task for Fujitsu. This readme will contain all the necessary information about this project.

## REST API documentation

This API consists of 2 endpoints. One for getting delivery fees (api/v1/delivery_fee) other for configuring business rules(api/v1/delivery_fee/config/)

## Delivery fee endpoint
```api/v1/deliveryfee```

Response example for delivery when weather conditions allow it.

```json
{
  "deliveryFee": 4,
  "weatherRecord": {
    "id": "cc3839a1-b5ad-4734-bb4a-0f27aa531d53",
    "timestamp": 1678702760,
    "cityName": "TALLINN",
    "phenomenon": "CLOUDY_WITH_CLEAR_SPELLS",
    "airTemperature": 0.6,
    "windSpeed": 5
  },
  "message": "Delivery allowed"
}
```

Response example when delivery on this vehicle type is forbidden.

```json
{
  "deliveryFee": null,
  "weatherRecord": {
    "id": "ad657bd6-498f-47e7-988b-0c0a84c2dbde",
    "timestamp": 1678708672,
    "cityName": "TALLINN",
    "phenomenon": "CLOUDY_WITH_CLEAR_SPELLS",
    "airTemperature": 1.2,
    "windSpeed": 5.2
  },
  "message": "Usage of selected vehicle type is forbidden"
}
```

### 'city' parameter
Parameter specifies which cities delivery fee and weather conditions will be used for calculations


All available parameter values(not case sensitive):

- tallinn
- tartu
- parnu
- pärnu

### 'vehicle' parameter

Parameter specifies what vehicle's calculation rules will be used.

All available parameter values(not case sensitive)

- car
- bike
- scooter

## Business rule configuration endpoint
```api/v1/delivery_fee/config/```

This endpoint allows to configure different fee calculation business rules and change delivery fees.

###Default rules:

####Business rules to calculate regional base fee (RBF):
######In case City = Tallinn and:

- Vehicle type = Car, then RBF = 4 €
- Vehicle type = Scooter, then RBF = 3,5 €
- Vehicle type = Bike, then RBF = 3 €

######In case City = Tartu and:
- Vehicle type = Car, then RBF = 3,5 €
- Vehicle type = Scooter, then RBF = 3 € 
- Vehicle type = Bike, then RBF = 2,5 €

######In case City = Pärnu and:
- Vehicle type = Car, then RBF = 3 €
- Vehicle type = Scooter, then RBF = 2,5 € 
- Vehicle type = Bike, then RBF = 2 €


####Business rules to calculate extra fees for weather conditions:

######Extra fee based on air temperature (ATEF) in a specific city is paid in case Vehicle type = Scooter or Bike and:
- Air temperature is less than -10 C, then ATEF = 1 €
- Air temperature is between -10 C and 0 C, then ATEF = 0,5 €

###### Extra fee based on wind speed (WSEF) in a specific city is paid in case Vehicle type = Bike and:
- Wind speed is between 10 m/s and 20 m/s, then WSEF = 0,5 €
- In case of wind speed is greater than 20 m/s, then the error message “Usage of selected vehicle
type is forbidden” has to be given

###### Extra fee based on weather phenomenon (WPEF) in a specific city is paid in case Vehicle type = Scooter or Bike and:
- Weather phenomenon is related to snow or sleet, then WPEF = 1 €
- Weather phenomenon is related to rain, then WPEF = 0,5 €
- In case the weather phenomenon is glaze, hail, or thunder, then the error message “Usage of
selected vehicle type is forbidden” has to be given


### API behaviour with custom business rules and fees
When this endpoint is not used and there are no custom rules configured, then API uses default rules, that can not be deleted. 

This endpoint provides CRUD functionality for every business rule.

When a new rule is added, then the default rule is not deleted, but the preference is given to the new rule. You can not add multiple rules that are valid at the same time(e.g 2 rules for weather phenomenon 'HEAVY SNOW SHOWER'). In order to do that you can modify the existing rule or delete it. When a custom rule is deleted, then calculations are done using default rules described above.

#### Regional base fee (RBF) configuration

JSON structure: 

```json
{
        "id": "180e5ece-c247-415f-8710-cd43921e6cf5",
        "cityName": "TALLINN",
        "vehicleFeeData": {
            "SCOOTER": 4.5,
            "CAR": 7.0,
            "BIKE": 4.0
        }
}
```

In ```vehicleFeeData``` every vehicle type must be specified. In case when it is needed to prohibit certain vehicle usage, then instead of delivery price ```null``` should be inserted.

```cityName``` values are ```TALLINN```, ```TARTU```, ```PARNU```. Should be inserted in ALL CAPS.


##### Adding RBF new rules
Request type: POST

Endpoint: ```/api/v1/delivery_fee/config/regional```

content-type: application/json

JSON body:

```json
{
    "vehicleFeeData":{
        "CAR":7.0,
        "BIKE":4.0,
        "SCOOTER":4.5
    },
    "cityName":"TALLINN"
}
```

JSON response in case of success:

```json
{
    "id": "0e985d59-a787-4f98-ae76-13f25ddf8a83",
    "cityName": "TALLINN",
    "deserializedVehicleFeeData": {
        "BIKE": 4.0,
        "CAR": 7.0,
        "SCOOTER": 4.5
    }
}
```

##### Getting all RBF rules

Request type: GET

Endpoint: ```/api/v1/delivery_fee/config/regional```

content-type: application/json

JSON body: empty

JSON response in case of success:

All RBF custom rules are outputed

```json
[
    {
        "id": "0e985d59-a787-4f98-ae76-13f25ddf8a83",
        "cityName": "TALLINN",
        "deserializedVehicleFeeData": {
            "BIKE": 4.0,
            "CAR": 7.0,
            "SCOOTER": 4.5
        }
    },
    {
        "id": "041ec1ac-029e-48d5-a42c-89f0ddd18951",
        "cityName": "TARTU",
        "deserializedVehicleFeeData": {
            "BIKE": 4.0,
            "CAR": 5.0,
            "SCOOTER": 3.5
        }
    }
]
```

##### Editing RBF rules

Request type: PUT

Endpoint: ```/api/v1/delivery_fee/config/regional```

content-type: application/json

JSON body:

```json
{
        "id": "180e5ece-c247-415f-8710-cd43921e6cf5",
        "cityName": "TALLINN",
        "vehicleFeeData": {
            "SCOOTER": 4.5,
            "CAR": 7.0,
            "BIKE": 4.0
        }
}
```

```id``` field should be the same as of the object that is being edited.

JSON response in case of success: empty


##### Delete custom RBF rule

Request type: PUT

Endpoint: ```/api/v1/delivery_fee/config/regional/{CITY_NAME}```

```CITY_NAME``` should alues are ```TALLINN```, ```TARTU```, ```PARNU```. Should be inserted in ALL CAPS.


content-type: application/json

JSON body: empty

JSON response in case of success: empty

#### Wind speed extra fee (WSEF) and air temperature extra fee(ATEF) configuration

JSON structure:

```json
{
    "minValue":5.0,
    "maxValue":7.0,
    "vehicleFeeData":{
        "BIKE": 2.5,
        "SCOOTER": 1.0,
        "CAR": 0.5
    },
    "valueUnit":"WIND_SPEED"
}
```

```minValue``` - minimal value from which this rule is active

```maxValue``` -  maximal value to which this rule is active



In ```vehicleFeeData``` every vehicle type must be specified. In case when it is needed to prohibit certain vehicle usage, then instead of delivery price ```null``` should be inserted.

```valueUnit``` possible values - ```WIND_SPEED``` for configuring WSEF and ```TEMPERATURE``` for configuring ATEF. Should be inserted in ALL CAPS.



