#!/bin/bash

javac modulesearchrmi/*.java
rmic modulesearchrmi.ModuleSearchServerImpl
java modulesearchrmi.ModuleSearchServerApp
