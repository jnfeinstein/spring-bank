.PHONY: build

build:
	@echo Uploading to $(S3_BUCKET)/$(S3_PREFIX)
	aws cloudformation package \
		--template-file main.template.yml \
		--s3-bucket $(S3_BUCKET) \
		--s3-prefix $(S3_PREFIX) \
		--output-template-file out.template.yml
	aws s3 cp out.template.yml s3://$(S3_BUCKET)/$(S3_PREFIX)/out.template.yml
