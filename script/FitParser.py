import os
import re
import subprocess
import argparse


def convert_fit(input_dir, output_dir):
    for root, dirs, files in os.walk(input_dir, topdown=False):
        for name in files:
            input_file = os.path.join(root, name)
            if re.match(".*\\.fit", input_file):
                print(os.path.join(root, name))
                output_file = os.path.join(output_dir, name).replace('.fit', '.gpx')
                cmd = str.format('gpsbabel -i garmin_fit -f %s -o gpx,gpxver=1.1 -F %s' % (input_file, output_file))
                subprocess.run(cmd, shell=True)

    print("Conversion finished!")


def check_valid_file(file_name):
    return os.path.isdir(file_name)


def main():
    desc = 'Converts a directory of fit files in a directory of gpx files'
    parser = argparse.ArgumentParser(description=desc)
    parser.add_argument("-i", "--input", help="Input directory")
    parser.add_argument("-o", "--output", help="Output directory")
    args = parser.parse_args()

    if args.input and args.output:
        if check_valid_file(args.input) and check_valid_file(args.output):
            convert_fit(args.input, args.output)
        else:
            print("Value passed as input and output are not valid directory")
    else:
        print("Please pass an input and output directory")


if __name__ == "__main__":
    main()
