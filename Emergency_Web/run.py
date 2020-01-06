from emergency_view import app
import sys

if len(sys.argv) < 2 :
	IP = "127.0.0.1"
else :
	IP = sys.argv[1]


if __name__ == "__main__":
	app.run(debug=True, host=IP)