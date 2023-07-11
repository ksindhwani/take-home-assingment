if [ "$#" -ne 2 ]; then
    echo "Please provide host directory and json file name in 2 arguments"
else
    host_directory="$1"
    file_name="$2"
    chmod -R 700 "$host_directory"
    docker build -t ksindhwani-golang-test .
    docker run -v "$host_directory:/app/data" --rm ksindhwani-golang-test -name "$file_name"
fi
