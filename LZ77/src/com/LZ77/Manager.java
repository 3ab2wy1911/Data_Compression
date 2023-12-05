package com.LZ77;

import java.util.Vector;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// Responsible for controlling the whole process.
public class Manager {
    private Vector<String> tags = new Vector<>(); // Vector of tags.
    Scanner scanner = new Scanner(System.in);

    //----------------------------------------------------------------

    //----------------------------------------------------------------

    // Function to write content from a vector into a file
    public void write_vector_to_file(Vector<String> content, String filename) {
        String str = "";
        for (String line : content) {
            str = str.concat(line);
        }
        write_string_to_file(str, filename);
    }

    //----------------------------------------------------------------

    // Function to write content from a string into a file
    public void write_string_to_file(String content, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------

    public void Display() {
        while (true) {
            System.out.println("Welcome to LZ77 !");
            System.out.println("""
                    Enter Your Choice :
                    1. Compression.
                    2. Decompression.
                    0. Exit.
                    """);
            int choice = scanner.nextInt();

            while (choice < 0 || choice > 2) {
                System.out.println("Invalid input !!! Please try again :");
                choice = scanner.nextInt();
            }
            if (choice == 0) {
                break;
            }
            String text;
            if (choice == 1) {
                System.out.println("Enter your text : ");
                scanner.nextLine();
                text = scanner.nextLine();
                Compression compression = new Compression(text);
                tags = compression.compress();
                write_vector_to_file(tags, "Compressed_text.txt");
                System.out.println("The text : " + text + ". Compressed Successfully into : " + tags);
            }

            //----------------------------------------------------------------

            else {
                System.out.println("Enter your tags : ");
                scanner.nextLine();
                String input = scanner.nextLine();
                input = input.replaceAll("\\s+", "");
                tags.clear();

                Pattern pattern = Pattern.compile("<[^>]+>");
                Matcher matcher = pattern.matcher(input);

                // Extracting tags and adding to the Vector
                while (matcher.find()) {
                    String tag = matcher.group().replaceAll("\\s", ""); // Remove spaces within the tag
                    tags.add(tag);
                }

                    Decompression decompression = new Decompression(tags);
                    text = decompression.decompress();
                    write_string_to_file(text, "Decompressed_text.txt");
                    System.out.println("The tags : " + tags + ". Decompressed Successfully into : " + text);
                }
            }
            //----------------------------------------------------------------
//            }
        }
    }
