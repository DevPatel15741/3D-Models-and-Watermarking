# 3D-Models-and-Watermarking
This program seeks to embed a watermark in an ASCII STL file. It is robust against scale and translation translations.
The files read must be in an ASCII format, not binary.

Encoder.java will take a message and preapare the STL file for embedding the watermark.
Decoder.java will read the message from a file.
Triangle.java contains all the methods necessary for Encoder.java and Decoder.java.
