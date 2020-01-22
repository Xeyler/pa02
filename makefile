submission/brigham_campbell.zip: 
	mkdir -p submission
	cd code/ && zip -r brigham_campbell.zip .
	mv code/brigham_campbell.zip submission/brigham_campbell.zip

.PHONY: clean
clean:
	rm -f submission/brigham_campbell.zip
