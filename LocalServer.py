from threading import Thread
import time
import webbrowser
import http.server
import socketserver
import json
import os
import sys  

port_number = 8000

server = None
def startServer(port):
    originDir = os.getcwd()
    os.chdir(os.path.dirname(os.path.realpath(__file__)))
    Handler = http.server.SimpleHTTPRequestHandler
    global server
    server = socketserver.TCPServer(("", port), Handler)

    print("serving at port", port)
    server.serve_forever()
    os.chdir(originDir)

def start(port):
    thread = Thread(target=startServer, args=[port])
    thread.start()
    time.sleep(2) #Wait to start the server first

def test():
    if not server:
        print("Failed to start server")

    url = "http://localhost:" + str(port_number) + '/' + 'index.html'
    url += "?number="
    url += "1"
    
    jsonObj = {
        "person": {
            "name": "Jack",
            "age": 20
        }
    }
  
    f=open("output.json")
    g=open("result.json","r+")
    setting = json.load(f)
    setting2 = json.load(g)
    g.close()
    os.remove("result.json")
    h=open("result.json","w+")
    for i in range(0,2):
      if setting2[i]["ANDROID_ID"]==setting["message"][0]["ANDROID_ID"]:
        setting2[i]=setting["message"][0]
        break  
        
    f.close()

    jsonStr=json.dumps(setting2,ensure_ascii=False)
    print(jsonStr,file=h)
    #jsonStr = json.dumps(jsonObj)
    url += "&person="
    url += jsonStr
    webbrowser.open(url)
    print(url + " is opened in browser")
    g.close()

def stop():
    if server:
        server.shutdown()

if __name__ == "__main__":
    while(1):
    	import importlib,sys
    	importlib.reload(sys)
    	start(port_number)
    	test()
    	time.sleep(50)
