#coding:utf-8
import sys

def find(anno_file, orig_file, pattern):
    pos_dict = {}
    for line in open(anno_file):
        items = line.split()
        if len(items) == 3:
            pos_dict[items[0]] = items[2]


    for line in open(orig_file):
        pos = ''
        for i in range(len(line) / 3):
            zi = line[i *3:i*3+3]
            if zi in pos_dict:
                pos += pos_dict[zi]
            else:
                pos += 'x'
        if len(pos) == len(pattern) and all(pos[i] == pattern[i] or pattern[i] == 'x' for i in range(len(pos))):
            print line




if __name__ == '__main__':
    find('annotation_final.txt', 'wordlist.txt', sys.argv[1])