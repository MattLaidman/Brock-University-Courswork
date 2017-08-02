#!/usr/bin/python
"""gensillhouette module for use in the COSC 1P91 ray tracing assignment.
Creates a sillhouette of the spheres in a given scene file in the immediate
parent folder.

Written By Matt Laidman (5199807)
April 15, 2015"""

from ctypes import *
import image

libraryName='./libmakesillhouette.so'
imglib=CDLL(libraryName)

def parseFile(sceneFile):
	"""parseFile function parses the given scene file and determines
	the location and radius of the spheres"""
	with open(sceneFile, 'r') as f:
		numSpheres = int(f.readline())
		spheres = (c_float * (numSpheres * 4))()
		for i in range(numSpheres):
			line = f.readline()
			k = 0
			for word in line.split():
				spheres[i*4+k] = float(word)
				k = k + 1
				if k % 4 == 0:
					break
	return (numSpheres, spheres)

def getVals():
	"""getVals function gets the information required for the
	image from the user."""
	while True:
		try:
			w = int(raw_input("\nEnter width: "))
			break
		except ValueError:
			print('Invalid width.')
	while True:
		try:
			h = int(raw_input("Enter height: "))
			break
		except ValueError:
			print('Invalid height.')
	f=raw_input("Enter scene file name: ")
	while True:
		try:
			p = int(raw_input("# of threads: "))
			break
		except ValueError:
			print('Invalid number of treads.')
	if w<1 or h<1 or p<1:
		return None
	return (w,h,f,p)

def menu():
	"""menu function is main menu for the module"""
	print '\n1. Make Sillhouette'
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
			imglib.processImage(dims[3], fileinfo[0], fileinfo[1])
			img.writetofile(outfile)
raise SystemExit(0)	