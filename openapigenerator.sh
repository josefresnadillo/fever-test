curl --insecure -H "accept: application/json" https://api.swaggerhub.com/apis/luis-pintado-feverup/backend-test/1.0.0 > services/events-api/src/main/resources/swagger.yaml
openapi-generator generate -i services/events-api/src/main/resources/swagger.yaml \
-g jaxrs-jersey \
-c ./openapiconfig.json \
-o services/events-api \
--package-name com.fever.events.primary.api \
--model-package com.fever.events.primary.api \
--global-property=models=,modelDocs=false
