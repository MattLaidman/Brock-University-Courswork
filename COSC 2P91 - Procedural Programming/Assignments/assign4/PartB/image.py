"""Image class module for use in the COSC 1P91 ray tracing assignment.

Written By Matt Laidman (5199807)
April 15, 2015"""

from ctypes import *
class image(object):
	"""Image object - Data is array with pixel colour information."""

	def __init__(self,width,height):
		"""Constructor initaializes width, height, and array size"""
		self.__width=width
		self.__height=height
		self.__data=(c_float*(3*width*height))()
	
	def writetofile(self,filename):
		"""Function writes data to a given file."""
		outfile=open(filename,'w')
		outfile.write('P3\n')
		outfile.write(str(self.width)+' '+str(self.height)+'\n')
		outfile.write('255\n')
		for h in range(self.height):
			for w in range(self.width):
				for c in range(3):
					outfile.write(str(int(self.data[h*self.width*3+w*3+c]*255))+' ')
			outfile.write('\n')
		outfile.close()
	
	@property
	def data(self):
		"""Raw image data."""
		return self.__data
	
	@property
	def width(self):
		"""Width of image."""
		return self.__width
	
	@property
	def height(self):
		"""Height of image."""
		return self.__height
	
	@data.setter
	def data(self,d):
		"""Can not set data directly."""
		raise AttributeError('Can not set data directly')
	
	@width.setter
	def width(self,w):
		"""Can not set width directly."""
		raise AttributeError('Can not set width directly')
	
	@height.setter
	def height(self,h):
		"""Can not set height directly."""
		raise AttributeError('Can not set height directly')

