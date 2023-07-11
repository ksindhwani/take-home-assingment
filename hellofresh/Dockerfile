FROM golang:1.18-alpine AS gobuild

ARG REVISION

WORKDIR /app

VOLUME /app/data

# Copy the Go application code
COPY . .

# Build the Go application
RUN go build -o ksindhwani-golang-test -mod=vendor -ldflags \
    "-X 'main.buildTimestamp=$(date '+%b %d %Y %T')' -X main.revision=$REVISION" \
   main.go


# Use a minimal base image for the final container
FROM alpine:3.15

# Copy the built executable from the previous stage
COPY --from=gobuild /app/ksindhwani-golang-test .

# Set the entry point for the container
ENTRYPOINT ["./ksindhwani-golang-test"]