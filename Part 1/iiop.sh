#!/bin/bash

javac modulesearchiiop/*.java
rmic -iiop modulesearchiiop.ModuleSearchServerImpl
java modulesearchiiop.ModuleSearchServerApp
