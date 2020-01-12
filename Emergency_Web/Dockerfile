FROM python:3.7-alpine

COPY requirements.txt /
RUN apk add gcc musl-dev python-dev libffi-dev openssl-dev
RUN pip install -r /requirements.txt



