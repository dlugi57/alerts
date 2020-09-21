# SafetyNet Alerts API

Its main purpose is to send information to emergency services.

### Prerequisites

What things you need to install the software and how to install them

- Java 1.8
- Maven 3.6.2
- Spring Boot 2.3.3

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

- https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

- https://maven.apache.org/install.html

3.Initialize Spring Boot 

- https://start.spring.io/

### Running App

Run application to set API services ready

### Testing

The app has unit tests and integration tests written. 
To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

`mvn test`

# API documentation 

## Person 

### Get person by first name and last name 

#### URL 

[http://localhost:8080/person/?firstName=John&lastName=Boyd](http://localhost:8080/person/?firstName=John&lastName=Boyd)

#### Method 

`GET`

#### Endpoint 

`person`

#### Parameters 

- firstName
- lastName

#### Result 

```json
{
    "firstName": "John",
    "lastName": "Boyd",
    "address": "1509 Culver St",
    "city": "Culver",
    "zip": "97451",
    "phone": "841-874-6512",
    "email": "jaboyd@email.com"
}
```

<br>

___
___
<br>
<br>
<br>
<br>



### Get persons 

#### URL 

[http://localhost:8080/persons](http://localhost:8080/persons)

#### Method 

`GET`

#### Endpoint 

`persons`


#### Result 

```json
[
    {
        "firstName": "John",
        "lastName": "Boyd",
        "address": "1509 Culver St",
        "city": "Culver",
        "zip": "97451",
        "phone": "841-874-6512",
        "email": "jaboyd@email.com"
    },
    {
        "firstName": "Jacob",
        "lastName": "Boyd",
        "address": "1509 Culver St",
        "city": "Culver",
        "zip": "97451",
        "phone": "841-874-6513",
        "email": "drk@email.com"
    },
    {
        "firstName": "Tenley",
        "lastName": "Boyd",
        "address": "1509 Culver St",
        "city": "Culver",
        "zip": "97451",
        "phone": "841-874-6512",
        "email": "tenz@email.com"
    },
    {
        "firstName": "Roger",
        "lastName": "Boyd",
        "address": "1509 Culver St",
        "city": "Culver",
        "zip": "97451",
        "phone": "841-874-6512",
        "email": "jaboyd@email.com"
    },
    {
        "firstName": "Felicia",
        "lastName": "Boyd",
        "address": "1509 Culver St",
        "city": "Culver",
        "zip": "97451",
        "phone": "841-874-6544",
        "email": "jaboyd@email.com"
    }
]
```

<br>

___
___
<br>
<br>
<br>
<br>


### Add Person

#### URL 

[http://localhost:8080/person](http://localhost:8080/person)

#### Method 

`POST`

#### Endpoint 

`person`

#### Body 

```json
{
    "firstName": "Piotr",
    "lastName": "Dlugosz",
    "address": "1509 Culver St",
    "city": "Culver",
    "zip": "97451",
    "phone": "841-874-6512",
    "email": "jaboyd@email.com"
}
```

#### Result 

In header 

[http://localhost:8080/person/?firstName=Piotr&lastName=Dlugosz](http://localhost:8080/person/?firstName=Piotr&lastName=Dlugosz)

<br>

___
___
<br>
<br>
<br>
<br>


### Update person 

#### URL 

[http://localhost:8080/person](http://localhost:8080/person)

#### Method 

`PUT`

#### Endpoint 

`person`

#### Parameters 

```json
{
    "firstName": "John",
    "lastName": "Boyd",
    "address": "1509 Culver St666"
}
```

#### Result 

In header

[http://localhost:8080/person/?firstName=John&lastName=Boyd](http://localhost:8080/person/?firstName=John&lastName=Boyd)

<br>

___
___
<br>
<br>
<br>
<br>


### Delete person 

#### URL 

[http://localhost:8080/person](http://localhost:8080/person)

#### Method 

`DELETE`

#### Endpoint 

`person`

#### Body 

```json
{
    "firstName": "John",
    "lastName": "Boyd"
}
```

#### Result 

Status OK 

<br>

___
___
<br>
<br>
<br>
<br>

## Medical record 

### Get medical records

#### URL 

[http://localhost:8080/medicalrecords](http://localhost:8080/medicalrecords)

#### Method 

`GET`

#### Endpoint 

`medicalrecords`



#### Result 

```json
[
    {
        "firstName": "John",
        "lastName": "Boyd",
        "birthdate": "1984-03-06",
        "medications": [
            "aznol:350mg",
            "hydrapermazol:100mg"
        ],
        "allergies": [
            "nillacilan"
        ]
    },
    {
        "firstName": "Jacob",
        "lastName": "Boyd",
        "birthdate": "1989-03-06",
        "medications": [
            "pharmacol:5000mg",
            "terazine:10mg",
            "noznazol:250mg"
        ],
        "allergies": []
    },
    {
        "firstName": "Tenley",
        "lastName": "Boyd",
        "birthdate": "2012-02-18",
        "medications": [],
        "allergies": [
            "peanut"
        ]
    }
]
```

<br>

___
___
<br>
<br>
<br>
<br>


### Get medical record by name and last name 

#### URL 

[http://localhost:8080/medicalrecord/?firstName=John&lastName=Boyd](http://localhost:8080/medicalrecord/?firstName=John&lastName=Boyd)

#### Method 

`GET`

#### Endpoint 

`medicalrecord`

#### Parameters 

- firstName
- lastName

#### Result 

```json
{
    "firstName": "John",
    "lastName": "Boyd",
    "birthdate": "1984-03-06",
    "medications": [
        "aznol:350mg",
        "hydrapermazol:100mg"
    ],
    "allergies": [
        "nillacilan"
    ]
}
```

<br>

___
___
<br>
<br>
<br>
<br>


### Add medical record  

#### URL 

[http://localhost:8080/medicalrecord](http://localhost:8080/medicalrecord)

#### Method 

`POST`

#### Endpoint 

`medicalrecord`

#### Body 

```json
{
    "firstName": "Piotr",
    "lastName": "Dlugosz",
    "birthdate": "12/06/1975",
    "medications": [
        "ibupurin:200mg",
        "hydrapermazol:400mg"
    ],
    "allergies": [
        "nillacilan"
    ]
}
```

#### Result 

In header 

http://localhost:8080/medicalrecord/?firstName=Piotr&lastName=Dlugosz

<br>

___
___
<br>
<br>
<br>
<br>


### Update medical record 

#### URL 

[http://localhost:8080/medicalrecord](http://localhost:8080/medicalrecord)

#### Method 

`PUT`

#### Endpoint 

`medicalrecord`

#### Body 

```json
{
    "firstName": "Piotr",
    "lastName": "Dlugosz",
    "birthdate": "12/06/1988",
    "medications": [
        "ibupurin:200mg",
        "hydrapermazol:400mg"
    ],
    "allergies": [
        "nillacilansdgfsfg"
    ]
}
```

#### Result 

In header

http://localhost:8080/medicalrecord/?firstName=Piotr&lastName=Dlugosz


<br>

___
___
<br>
<br>
<br>
<br>


### Delete medical record 

#### URL 

[http://localhost:8080/medicalrecord](http://localhost:8080/medicalrecord)

#### Method 

`DELETE`

#### Endpoint 

`medicalrecord`

#### Parameters 

```json
{
    "firstName": "Piotr",
    "lastName": "Dlugosz"
}
```

#### Result 

Status OK

<br>

___
___
<br>
<br>
<br>
<br>

## Fire stations

### Get fire stations 

#### URL 

[http://localhost:8080/firestations](http://localhost:8080/firestations)

#### Method 

`GET`

#### Endpoint 

`firestations`


#### Result 

```json
[
    {
        "address": "1509 Culver St",
        "station": 3
    },
    {
        "address": "29 15th St",
        "station": 2
    },
    {
        "address": "834 Binoc Ave",
        "station": 3
    },
    {
        "address": "644 Gershwin Cir",
        "station": 1
    },
    {
        "address": "748 Townings Dr",
        "station": 3
    }
]
```

<br>

___
___
<br>
<br>
<br>
<br>


### Get fire stations by station id 

#### URL 

[http://localhost:8080/firestations/1](http://localhost:8080/firestations/1)

#### Method 

`GET`

#### Endpoint 

`firestations`

#### Parameters 

- id (in URL)

#### Result 

```json
[
    {
        "address": "644 Gershwin Cir",
        "station": 1
    },
    {
        "address": "908 73rd St",
        "station": 1
    },
    {
        "address": "947 E. Rose Dr",
        "station": 1
    }
]
```

<br>

___
___
<br>
<br>
<br>
<br>


### Get fire station by address

#### URL 

[http://localhost:8080/firestation/1509 Culver St](http://localhost:8080/firestation/1509 Culver St)

#### Method 

`GET`

#### Endpoint 

`firestation`

#### Parameters 

- address (in URL)

#### Result 

```json
{
    "address": "1509 Culver St",
    "station": 3
}
```

<br>

___
___
<br>
<br>
<br>
<br>


### Add fire station  

#### URL 

[http://localhost:8080/firestation](http://localhost:8080/firestation)

#### Method 

`POST`

#### Endpoint 

`firestation`

#### Parameters 

```json
{
        "address": "10 Forest Avenue",
        "station": 3
}
``` 

#### Result 

http://localhost:8080/firestation/10%20Forest%20Avenue

<br>

___
___
<br>
<br>
<br>
<br>


### Update fire station

#### URL 

[http://localhost:8080/firestation](http://localhost:8080/firestation)

#### Method 

`PUT`

#### Endpoint 

`firestation`

#### Parameters 


```json
{
    "address": "1509 Culver St",
    "station": 666
}
```

#### Result 

http://localhost:8080/firestation/?address=1509%20Culver%20St

<br>

___
___
<br>
<br>
<br>
<br>


### Delete fire station 

#### URL 

[http://localhost:8080/firestation](http://localhost:8080/firestation)

#### Method 

`DELETE`

#### Endpoint 

`firestation`

#### Parameters 

{
    "address": "1509 Culver St",
    "station": 3
}

#### Result 

Status OK

<br>

___
___
<br>
<br>
<br>
<br>


### Get persons in fire station area

#### URL 

[http://localhost:8080/firestation?stationNumber=3](http://localhost:8080/firestation?stationNumber=3)

#### Method 

`GET`

#### Endpoint 

`firestation`

#### Parameters 

- stationNumber

#### Result 

```json
{
    "childQty": 1,
    "adultQty": 5,
    "persons": [
        {
            "firstName": "Tessa",
            "lastName": "Carman",
            "address": "834 Binoc Ave",
            "phone": "841-874-6512"
        },
        {
            "firstName": "Foster",
            "lastName": "Shepard",
            "address": "748 Townings Dr",
            "phone": "841-874-6544"
        },
        {
            "firstName": "Clive",
            "lastName": "Ferguson",
            "address": "748 Townings Dr",
            "phone": "841-874-6741"
        },
        {
            "firstName": "Tony",
            "lastName": "Cooper",
            "address": "112 Steppes Pl",
            "phone": "841-874-6874"
        },
        {
            "firstName": "Ron",
            "lastName": "Peters",
            "address": "112 Steppes Pl",
            "phone": "841-874-8888"
        },
        {
            "firstName": "Allison",
            "lastName": "Boyd",
            "address": "112 Steppes Pl",
            "phone": "841-874-9888"
        }
    ]
}
```

<br>

___
___
<br>
<br>
<br>
<br>


### Get children by address

#### URL 

[http://localhost:8080/childAlert?address=1509 Culver St](http://localhost:8080/childAlert?address=1509 Culver St)

#### Method 

`GET`

#### Endpoint 

`childAlert`

#### Parameters 

- address

#### Result 

```json
{
    "adults": [
        {
            "firstName": "Jacob",
            "lastName": "Boyd",
            "age": 31
        },
        {
            "firstName": "Felicia",
            "lastName": "Boyd",
            "age": 34
        },
        {
            "firstName": "Piotr",
            "lastName": "Dlugosz",
            "age": 32
        }
    ],
    "children": [
        {
            "firstName": "Tenley",
            "lastName": "Boyd",
            "age": 8
        },
        {
            "firstName": "Roger",
            "lastName": "Boyd",
            "age": 3
        }
    ]
}
```

<br>

___
___
<br>
<br>
<br>
<br>


### Get phone numbers in fire station area 

#### URL 

[http://localhost:8080/phoneAlert?stationNumber=1](http://localhost:8080/phoneAlert?stationNumber=1)

#### Method 

`GET`

#### Endpoint 

`phoneAlert`

#### Parameters 

- stationNumber

#### Result 

```json
[
    "841-874-6512",
    "841-874-8547",
    "841-874-7462",
    "841-874-7784",
    "841-874-7784",
    "841-874-7784"
]
```

<br>

___
___
<br>
<br>
<br>
<br>


### Get persons and station by address

#### URL 

[http://localhost:8080/fire?address=1509 Culver St](http://localhost:8080/fire?address=1509 Culver St)

#### Method 

`GET`

#### Endpoint 

`fire`

#### Parameters 

- address

#### Result 

```json
{
    "station": 666,
    "persons": [
        {
            "lastName": "Boyd",
            "phone": "841-874-6513",
            "age": 31,
            "medications": [
                "pharmacol:5000mg",
                "terazine:10mg",
                "noznazol:250mg"
            ],
            "allergies": []
        },
        {
            "lastName": "Boyd",
            "phone": "841-874-6512",
            "age": 8,
            "medications": [],
            "allergies": [
                "peanut"
            ]
        },
        {
            "lastName": "Boyd",
            "phone": "841-874-6512",
            "age": 3,
            "medications": [],
            "allergies": []
        },
        {
            "lastName": "Boyd",
            "phone": "841-874-6544",
            "age": 34,
            "medications": [
                "tetracyclaz:650mg"
            ],
            "allergies": [
                "xilliathal"
            ]
        },
        {
            "lastName": "Dlugosz",
            "phone": "841-874-6512",
            "age": 32,
            "medications": [
                "ibupurin:200mg",
                "hydrapermazol:400mg"
            ],
            "allergies": [
                "nillacilansdgfsfg"
            ]
        }
    ]
}
```

<br>

___
___
<br>
<br>
<br>
<br>


### Get persons and addresses by stations 

#### URL 

[http://localhost:8080/flood/stations?stations=1,3](http://localhost:8080/flood/stations?stations=1,3)

#### Method 

`GET`

#### Endpoint 

`flood/stations`

#### Parameters 

- stations

#### Result 

```json
[
    {
        "address": "644 Gershwin Cir",
        "persons": [
            {
                "lastName": "Duncan",
                "phone": "841-874-6512",
                "age": 20,
                "medications": [],
                "allergies": [
                    "shellfish"
                ]
            }
        ]
    },
    {
        "address": "908 73rd St",
        "persons": [
            {
                "lastName": "Walker",
                "phone": "841-874-8547",
                "age": 41,
                "medications": [
                    "thradox:700mg"
                ],
                "allergies": [
                    "illisoxian"
                ]
            },
            {
                "lastName": "Peters",
                "phone": "841-874-7462",
                "age": 38,
                "medications": [],
                "allergies": []
            }
        ]
    },
    {
        "address": "947 E. Rose Dr",
        "persons": [
            {
                "lastName": "Stelzer",
                "phone": "841-874-7784",
                "age": 44,
                "medications": [
                    "ibupurin:200mg",
                    "hydrapermazol:400mg"
                ],
                "allergies": [
                    "nillacilan"
                ]
            },
            {
                "lastName": "Stelzer",
                "phone": "841-874-7784",
                "age": 40,
                "medications": [],
                "allergies": []
            },
            {
                "lastName": "Stelzer",
                "phone": "841-874-7784",
                "age": 6,
                "medications": [
                    "noxidian:100mg",
                    "pharmacol:2500mg"
                ],
                "allergies": []
            }
        ]
    },
    {
        "address": "834 Binoc Ave",
        "persons": [
            {
                "lastName": "Carman",
                "phone": "841-874-6512",
                "age": 8,
                "medications": [],
                "allergies": []
            }
        ]
    },
    {
        "address": "748 Townings Dr",
        "persons": [
            {
                "lastName": "Shepard",
                "phone": "841-874-6544",
                "age": 40,
                "medications": [],
                "allergies": []
            },
            {
                "lastName": "Ferguson",
                "phone": "841-874-6741",
                "age": 26,
                "medications": [],
                "allergies": []
            }
        ]
    },
    {
        "address": "10 Forest Avenue",
        "persons": [
            {
                "lastName": "Cooper",
                "phone": "841-874-6874",
                "age": 26,
                "medications": [
                    "hydrapermazol:300mg",
                    "dodoxadin:30mg"
                ],
                "allergies": [
                    "shellfish"
                ]
            },
            {
                "lastName": "Peters",
                "phone": "841-874-8888",
                "age": 55,
                "medications": [],
                "allergies": []
            },
            {
                "lastName": "Boyd",
                "phone": "841-874-9888",
                "age": 55,
                "medications": [
                    "aznol:200mg"
                ],
                "allergies": [
                    "nillacilan"
                ]
            }
        ]
    },
    {
        "address": "10 Forest Avenue",
        "persons": [
            {
                "lastName": "Cooper",
                "phone": "841-874-6874",
                "age": 26,
                "medications": [
                    "hydrapermazol:300mg",
                    "dodoxadin:30mg"
                ],
                "allergies": [
                    "shellfish"
                ]
            },
            {
                "lastName": "Peters",
                "phone": "841-874-8888",
                "age": 55,
                "medications": [],
                "allergies": []
            },
            {
                "lastName": "Boyd",
                "phone": "841-874-9888",
                "age": 55,
                "medications": [
                    "aznol:200mg"
                ],
                "allergies": [
                    "nillacilan"
                ]
            }
        ]
    }
]
```

<br>

___
___
<br>
<br>
<br>
<br>


### Get person info

#### URL 

[http://localhost:8080/personInfo?firstName=John&lastName=Boyd](http://localhost:8080/personInfo?firstName=John&lastName=Boyd)

#### Method 

`GET`

#### Endpoint 

`personInfo`

#### Parameters 

- firstName
- lastName

#### Result 

```json
[
    {
        "lastName": "Boyd",
        "address": "1509 Culver St",
        "age": 36,
        "email": "jaboyd@email.com",
        "medications": [
            "aznol:350mg",
            "hydrapermazol:100mg"
        ],
        "allergies": [
            "nillacilan"
        ]
    },
    {
        "lastName": "Boyd",
        "address": "1509 Culver St",
        "age": 31,
        "email": "jaboyd@email.com",
        "medications": [
            "pharmacol:5000mg",
            "terazine:10mg",
            "noznazol:250mg"
        ],
        "allergies": []
    },
    {
        "lastName": "Boyd",
        "address": "1509 Culver St",
        "age": 8,
        "email": "jaboyd@email.com",
        "medications": [],
        "allergies": [
            "peanut"
        ]
    },
    {
        "lastName": "Boyd",
        "address": "1509 Culver St",
        "age": 3,
        "email": "jaboyd@email.com",
        "medications": [],
        "allergies": []
    },
    {
        "lastName": "Boyd",
        "address": "1509 Culver St",
        "age": 34,
        "email": "jaboyd@email.com",
        "medications": [
            "tetracyclaz:650mg"
        ],
        "allergies": [
            "xilliathal"
        ]
    }
]
```

<br>

___
___
<br>
<br>
<br>
<br>


### Get community emails

#### URL 

[http://localhost:8080/communityEmail?city=Culver](http://localhost:8080/communityEmail?city=Culver)

#### Method 

`GET`

#### Endpoint 

`communityEmail`

#### Parameters 

- city

#### Result 

```json
[
    "jaboyd@email.com",
    "drk@email.com",
    "tenz@email.com",
    "jaboyd@email.com",
    "jaboyd@email.com",
    "drk@email.com",
    "tenz@email.com",
    "jaboyd@email.com",
    "jaboyd@email.com",
    "tcoop@ymail.com",
    "lily@email.com",
    "soph@email.com",
    "ward@email.com",
    "zarc@email.com",
    "reg@email.com",
    "jpeter@email.com",
    "jpeter@email.com",
    "aly@imail.com",
    "bstel@email.com",
    "ssanw@email.com",
    "bstel@email.com",
    "clivfd@ymail.com",
    "gramps@email.com"
]
```
