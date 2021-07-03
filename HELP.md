# Recipe Stats Calculator CLI

---

This page guides how to set up the program and use it.

## Installation

---

Clone the repository to your local system, go to the repository folder and start building.

### Build without docker

The program also can be build and run using Docker (See below).

Run tests & build jar using `gradle`.

```shell
gradle test jar
```

Run

```shell
java -jar ./build/libs/delivery-stat-calculator-1.0.jar -f "/path/custom.json" -s "Veggie,Chicken" -p "10120" -t "10AM-3PM"
```

Usage: Recipe Stats Calculator CLI

``` 
  -f, --file            File name with full path
  -p, --postcode        Postcode      e.g: 10120
  -s, --search_terms    Search terms. e.g: Chicken, Avacado
  -t, --time_range      Time range.   e.g: 5AM-2PM

```

### Run with docker

Build the docker image. Tests also is run during while building docker.

```shell
docker build -t "delivery-stat-count" .
```

Run the docker image with default parameters.

```shell
docker run -it --rm "delivery-stat-count"
```

Use environmental variables to pass CLI arguments to the docker container.

```shell
docker run -it --rm -e SEARCH_TERMS=chicken,veggie "delivery-stat-count"
```

Available environmental variables:

```
FILE         - file path for custom input. Default value: sample json 
SEARCH_TERMS - recipe names to search by. Default: Potato, Veggie, Mushroom
POSTCODE     - postcode. Default: 10120
TIME_RANGE   - time window. Default: 
```

> NOTE: Mount cutom file location if you want to provide a custom fixture file as input

Go to the directory the custom file is located and run the docker command from there. This command mounts the current
directory to the container's data folder, and the program inside docker container searches the file from `/data` folder.

```
docker run -it --rm -v ${PWD}:/data -e FILE=custom.json "delivery-stat-count"
``` 
