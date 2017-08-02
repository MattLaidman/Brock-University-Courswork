#!/usr/bin/python
"""genscene module for use in the COSC 1P91 ray tracing assignment.
Creates a scene fromt the spheres and light sources in the given scene
file in it's folder.

Matt Laidman (5199807)
April 15, 2015"""

from ctypes import *
import image

libraryName='./libmakescene.so'
imglib=CDLL(libraryName)

def parseFile(sceneFile):
	"""parseFile function parses the given scene file and determines
	the location and radius of the spheres, the locations and colours
	of the lights, and the ambient light."""
	with open(sceneFile, 'r') as f:
		numSpheres = int(f.readline())
		spheres = (c_float * (numSpheres * 9))()
		i = 0;
		for j in range(numSpheres):
			line = f.readline()
			for word in line.split():
				spheres[i] = float(word)
				i = i + 1
		numLights = int(f.readline())
		lights = (c_float * (numLights * 6 + 3))()
		i = 0;
		for j in range(numLights):
			line = f.readline()
			for word in line.split():
				lights[3+i] = float(word)
				i = i + 1
		i = 0;
		line = f.readline()
		for word in line.split():
			lights[i] = float(word)
			i = i + 1
		return (numSpheres, spheres, numLights, lights)

def getVals():
	"""getVals function gets the information required for the
	image from the user."""
	while True:
		try:
			w=int(raw_input("\nEnter width: "))
			break
		except ValueError:
			print('Invalid width.')
	while True:
		try:
			h=int(raw_input("Enter height: "))
			break
		except ValueError:
			print('Invalid height.')
	f=raw_input("Enter scene file name: ")
	while True:
		try:
			p=int(raw_input("# of threads: "))
			break
		except ValueError:
			print('Invalid number of treads.')
	if w<1 or h<1 or p<1:
		return None
	return (w,h,f,p)

def menu():
	"""menu function is main menu for the module"""
	print '\n1. Make Scene'
	print '0. Quit\n'
	return raw_input()

choice = -1
while choice != '0':
	choice = menu()
	if choice == '1':
		dims = getVals()
		if dims:
			outfile = raw_input('Output Filename: ')
			img = image.image(width = dims[0],height = dims[1])
			imglib.initializeImage(img.width,img.height,img.data)
			fileinfo = parseFile(dims[2]);
			imglib.processImage(dims[3], fileinfo[0], fileinfo[1], fileinfo[2], fileinfo[3])
			img.writetofile(outfile)
raise SystemExit(0)