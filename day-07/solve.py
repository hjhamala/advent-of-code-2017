import numpy as np
import traceback

def get_weight(xs):
    return "".join([s for s in list(xs) if s.isdigit()])


def get_childrens(s):
    if "->" in s:
        return [v.strip() for v in s.split("->")[1].split(",")]
    else:
        return []


def get_name(s):
    return s.split(" ")[0]


def parse(row):
    return {"name": get_name(row),
            "children": get_childrens(row),
            "weight": get_weight(row)}


def get_values():
    with open("input.txt") as f:
        return f.read().splitlines()


def solve(rows):
    vals = {}
    for row in rows:
        parsed = parse(row)
        name = parsed["name"]
        if name not in dict:
            vals[name] = {"parent": {}}
        for child in parsed["children"]:
            if child not in dict:
                vals[child] = {}
            vals[child]["parent"]=parsed["name"]
    print(dict)
    return [x for x in vals if vals[x]['parent'] == {}]

