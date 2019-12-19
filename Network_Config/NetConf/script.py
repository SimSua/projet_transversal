#!/usr/bin/python3

import yaml
from jinja2 import Environment, FileSystemLoader

ENV = Environment(loader=FileSystemLoader('.'))
baseline = ENV.get_template("template.j2")
 
with open("config.yaml") as y:
 host_obj = yaml.safe_load(y)
 f = open('router.conf', 'w')
 config = baseline.render(host=host_obj)
 f.write(config)
 f.close
