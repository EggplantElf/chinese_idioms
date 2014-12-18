import sys


def freq(filename):
    dic = {}
    for line in open(filename):
        line = line.strip()
        for i in range(len(line) / 3):
            zi = line[i *3:i*3+3]
            if zi not in dic:
                dic[zi] = 1
            else:
                dic[zi] += 1
    print len([k for k in dic if dic[k] > 10])

    # for zi in sorted(dic, key = lambda x: dic[x], reverse = True):
        # print zi, dic[zi]



if __name__ == '__main__':
    freq(sys.argv[1])