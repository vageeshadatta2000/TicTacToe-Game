#!/usr/bin/env python3
import json
import os
import sys
from urllib import request, parse

ROOT = "/workspace"

FILES = [
    "index.html",
    "styles.css",
    "script.js",
    "README.md",
]

def read_file(path):
    with open(path, "r", encoding="utf-8") as f:
        return f.read()

def build_payload():
    files_obj = {
        "sandbox.config.json": {"content": json.dumps({"template": "static"})},
    }
    for rel in FILES:
        full = os.path.join(ROOT, rel)
        if not os.path.exists(full):
            print(f"WARN: missing {rel}", file=sys.stderr)
            continue
        files_obj[rel] = {"content": read_file(full)}
    return {"files": files_obj}

def post_define(payload):
    url = "https://codesandbox.io/api/v1/sandboxes/define?json=1"
    data = json.dumps(payload).encode("utf-8")
    req = request.Request(url, data=data, headers={"Content-Type": "application/json"})
    with request.urlopen(req) as resp:
        body = resp.read()
        return json.loads(body.decode("utf-8"))

def main():
    payload = build_payload()
    resp = post_define(payload)
    print(json.dumps(resp, indent=2))
    sandbox_id = None
    # try common keys
    for key in ("sandbox_id", "sandboxId", "id", "sandboxID"):
        if key in resp and isinstance(resp[key], str):
            sandbox_id = resp[key]
            break
    if not sandbox_id:
        # sometimes nested data
        data = resp.get("data") or {}
        for key in ("sandbox_id", "sandboxId", "id", "sandboxID"):
            if key in data and isinstance(data[key], str):
                sandbox_id = data[key]
                break
    if sandbox_id:
        editor_url = f"https://codesandbox.io/s/{sandbox_id}"
        app_url = f"https://{sandbox_id}.csb.app"
        print("\nFriendly URLs:")
        print(f"Editor: {editor_url}")
        print(f"Live App: {app_url}")
    else:
        print("\nCould not determine sandbox id. See JSON above.")

if __name__ == "__main__":
    main()

