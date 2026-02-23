// Get our payload object typed inside our script
record Request(String message, int number) {}

var request = new Request(payload.message, payload.number);

if (request.message().startsWith("hello there")) {
    return "General Kenobi"
}

if (request.number() == 67) {
    return "SIX SEVEN"
}

return "EMPTY"