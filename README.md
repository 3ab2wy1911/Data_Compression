# Data_Compression

## Overview

This repository contains implementations of various data compression algorithms. The primary goal is to provide a comprehensive understanding of how these algorithms work and to demonstrate their effectiveness in compressing data. The algorithms included in this repository are:

- LZW (Lempel-Ziv-Welch)
- LZ77 (Lempel-Ziv 1977)
- Standard Huffman Coding
- Vector Quantization
- LBG (Linde-Buzo-Gray) Algorithm

## Algorithms

### LZW (Lempel-Ziv-Welch)

LZW is a lossless data compression algorithm that works by encoding sequences of characters into fixed-size codes. It is widely used in Unix file compression (compress command) and GIF image format.

### LZ77 (Lempel-Ziv 1977)

LZ77 is a lossless data compression algorithm that uses a sliding window to find and replace repeated occurrences of data with references to a single copy of that data existing earlier in the uncompressed stream.

### Standard Huffman Coding

Huffman coding is a lossless data compression algorithm. It uses a variable-length code table for encoding a source symbol (such as a character in a file) where the variable-length codes are derived from the frequency of occurrence of the source symbols.

### Vector Quantization

Vector quantization is a lossy data compression technique based on the principle of quantizing vectors from a large vector space to a finite number of regions in that space. Each region is represented by a code vector, and the index of this code vector is used for compression.

### LBG (Linde-Buzo-Gray) Algorithm

The LBG algorithm is an iterative algorithm used for vector quantization, primarily in speech and image compression. It is used to design a codebook for quantizing vectors in a source.

