.PHONY: docker-login docker-push

docker-login:
	@ aws ecr get-login-password --region $(AWS_REGION) | \
 		docker login --username AWS --password-stdin $(AWS_ACCOUNT_ID).dkr.ecr.$(AWS_REGION).amazonaws.com

docker-push:
	docker tag spring-bank/spring-bank-api:latest \
		"$(AWS_ACCOUNT_ID).dkr.ecr.$(AWS_REGION).amazonaws.com/$(ECR_REPOSITORY):spring-bank-api.$(TAG)"
	docker push "$(AWS_ACCOUNT_ID).dkr.ecr.$(AWS_REGION).amazonaws.com/$(ECR_REPOSITORY):spring-bank-api.$(TAG)"
