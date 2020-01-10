from emergency_view import app
import sys

if len(sys.argv) < 2 :
	IP = "164.4.1.10"
else :
	IP = sys.argv[1]


if __name__ == "__main__":
	app.run(debug=True, host=IP)
