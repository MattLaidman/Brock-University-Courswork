#!/usr/bin/python
"""gencolours module for use in the COSC 1P91 ray tracing assignment.
Creates sample PPM files with increasing red and blue values as
widths and height increases of a given width and height.

Written By Matt Laidman (5199807)
April 15, 2015"""

from ctypes import *
import image

libraryName='./libmakecolours.so'
imglib=CDLL(libraryName)

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
	while True:
		try:
			p = int(raw_input("# of threads: "))
			break
		except ValueError:
			print('Invalid number of threads.')
	if w<1 or h<1 or p<1:
		return None
	return (w,h,p)

def menu():
	"""menu function is main menu for the module"""
	print '\n1. Create Colours'
	print '0. Quit\n'
	return raw_input()

choice = -1
while choice != '0':
	choice = menu()
	if choice == '1':
		dims = getVals()
		if dims:
			filename = raw_input('Output Filename: ')
			img = image.image(width = dims[0],height = dims[1])
			imglib.initializeImage(img.width,img.height,img.data)
			imglib.processImage(dims[2])
			img.writetofile(filename)
raise SystemExit(0)
