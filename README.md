# promotion-engine
Promotion Engine service.

## Table of Contents
- [Test using Rest APIs](#rest)
- [Test using Integration Test](#integration)

This is spring boot service that we can run and test the following scenarios:
There are 2 ways we can verify the result:

<a name="rest"></a>
## Using REST apis:

##### STEP1: Create sku data:
###### Create sku A:
```
curl --location --request POST 'http://localhost:8080/skus' \
--header 'Content-Type: application/json' \
--data-raw '{
    "skuId": "A",
    "skuPrice": "50"
}'
```

###### Create sku B:
```
curl --location --request POST 'http://localhost:8080/skus' \
--header 'Content-Type: application/json' \
--data-raw '{
    "skuId": "B",
    "skuPrice": "30"
}'
```

###### Create sku C:
```
curl --location --request POST 'http://localhost:8080/skus' \
--header 'Content-Type: application/json' \
--data-raw '{
    "skuId": "C",
    "skuPrice": "20"
}'
```

###### Create sku D:
```
curl --location --request POST 'http://localhost:8080/skus' \
--header 'Content-Type: application/json' \
--data-raw '{
    "skuId": "D",
    "skuPrice": "15"
}'
```
Verify SKUs:
```
curl --location --request GET 'http://localhost:8080/skus/'
```

##### Create SKU data looks like:
```
{
    "_embedded": {
        "skus": [
            {
                "skuId": "C",
                "skuPrice": 20.0,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/skus/7a5f02d6-a696-4b09-8f6b-1d341817f03f"
                    },
                    "sku": {
                        "href": "http://localhost:8080/skus/7a5f02d6-a696-4b09-8f6b-1d341817f03f"
                    }
                }
            },
            {
                "skuId": "A",
                "skuPrice": 50.0,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/skus/a8b17412-2158-4954-8b1d-92902f025ac4"
                    },
                    "sku": {
                        "href": "http://localhost:8080/skus/a8b17412-2158-4954-8b1d-92902f025ac4"
                    }
                }
            },
            {
                "skuId": "B",
                "skuPrice": 30.0,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/skus/ae7aecf2-89ed-4cca-8cec-b37a3acd8feb"
                    },
                    "sku": {
                        "href": "http://localhost:8080/skus/ae7aecf2-89ed-4cca-8cec-b37a3acd8feb"
                    }
                }
            },
            {
                "skuId": "D",
                "skuPrice": 15.0,
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/skus/1decc880-aca5-42bf-8be8-30c6de06cfae"
                    },
                    "sku": {
                        "href": "http://localhost:8080/skus/1decc880-aca5-42bf-8be8-30c6de06cfae"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/skus/"
        },
        "profile": {
            "href": "http://localhost:8080/profile/skus"
        },
        "search": {
            "href": "http://localhost:8080/skus/search"
        }
    },
    "page": {
        "size": 20,
        "totalElements": 4,
        "totalPages": 1,
        "number": 0
    }
}
```

#### STEP2: Create promotion rules:
##### Create promotion for sku A:
```
curl --location --request POST 'http://localhost:8080/promotions' \
--header 'Content-Type: application/json' \
--data-raw '{
    "skuId": "A",
    "rule": "2A=45"
}'
```

##### Create promotion for sku B:
```
curl --location --request POST 'http://localhost:8080/promotions' \
--header 'Content-Type: application/json' \
--data-raw '{
    "skuId": "B",
    "rule": "2B=45"
}'
```

##### Create promotion for sku C:
```
curl --location --request POST 'http://localhost:8080/promotions' \
--header 'Content-Type: application/json' \
--data-raw '{
    "skuId": "C",
    "rule": "C+D=30"
}'
```

##### Create promotion for sku D:
```
curl --location --request POST 'http://localhost:8080/promotions' \
--header 'Content-Type: application/json' \
--data-raw '{
    "skuId": "D",
    "rule": "C+D=30"
}'
```

##### Create promotions data look like:
```
{
    "_embedded": {
        "promotions": [
            {
                "skuId": "D",
                "rule": "C+D=30",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/promotions/70d52fcf-60dd-4370-8544-dd1ea4fffc32"
                    },
                    "promotion": {
                        "href": "http://localhost:8080/promotions/70d52fcf-60dd-4370-8544-dd1ea4fffc32"
                    }
                }
            },
            {
                "skuId": "C",
                "rule": "C+D=30",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/promotions/5b4b8289-52cb-4d47-a991-281d99b3a78c"
                    },
                    "promotion": {
                        "href": "http://localhost:8080/promotions/5b4b8289-52cb-4d47-a991-281d99b3a78c"
                    }
                }
            },
            {
                "skuId": "A",
                "rule": "3A=130",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/promotions/f340b14b-c709-4eea-bd06-f193f7ce27db"
                    },
                    "promotion": {
                        "href": "http://localhost:8080/promotions/f340b14b-c709-4eea-bd06-f193f7ce27db"
                    }
                }
            },
            {
                "skuId": "B",
                "rule": "2B=45",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/promotions/085e7a26-a025-47b4-b335-49ef86cbccfe"
                    },
                    "promotion": {
                        "href": "http://localhost:8080/promotions/085e7a26-a025-47b4-b335-49ef86cbccfe"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/promotions"
        },
        "profile": {
            "href": "http://localhost:8080/profile/promotions"
        },
        "search": {
            "href": "http://localhost:8080/promotions/search"
        }
    },
    "page": {
        "size": 20,
        "totalElements": 4,
        "totalPages": 1,
        "number": 0
    }
}
```

#### STEP3:Calculate promotion:
#### Scenario 1:
```
curl --location --request POST 'http://localhost:8080/promotions/actions/eval' \
--header 'Content-Type: application/json' \
--data-raw '{
    "details": [
        {
            "skuId": "A",
            "quantity": "1"
        },
        {
            "skuId": "B",
            "quantity": "1"
        },
        {
            "skuId": "C",
            "quantity": "1"
        }
    ]
}'
```
#### Result:
```
{
    "total": 100.0
}
```

#### Scenario 2:
```
curl --location --request POST 'http://localhost:8080/promotions/actions/eval' \
--header 'Content-Type: application/json' \
--data-raw '{
    "details": [
        {
            "skuId": "A",
            "quantity": "5"
        },
        {
            "skuId": "B",
            "quantity": "5"
        },
        {
            "skuId": "C",
            "quantity": "1"
        }
    ]
}'
```
#### Result:
```
{
    "total": 370.0
}
```

#### Scenario 3:
```
curl --location --request POST 'http://localhost:8080/promotions/actions/eval' \
--header 'Content-Type: application/json' \
--data-raw '{
    "details": [
        {
            "skuId": "A",
            "quantity": "3"
        },
        {
            "skuId": "B",
            "quantity": "5"
        },
        {
            "skuId": "C",
            "quantity": "1"
        },
        {
            "skuId": "D",
            "quantity": "1"
        }
    ]
}'
```
#### Result:
```
{
    "total": 280.0
}
```

<a name="integration"></a>
## 2-We can runt the integration test:
PromotionControllerTest and verify the scenarios:
````
Scenario 1
1 * A 50
1 * B 30
1* C 20
Total 100

Scenario 2
5 * A 130 + 2*50
5 * B 45 + 45 + 30
1 * C 28
Total 370

Scenario 3
3* A 130
5* B 45 + 45 + 1 * 30
1* C-
1* D 30

Total 280
````
