#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main(int argc, char *argv[])
{
  FILE *fp;
  int file_length, file_cur = 0;
  char byte_data, word_pos = 0;
  char word[50];
  if (argc != 2)
    {
      printf("usage: ew <dictname.idx>\n");
      exit (1);
    }
  fp = fopen(argv[1], "rb+");
  fseek(fp, 0, SEEK_END);
  file_length = ftell(fp);
  fseek(fp, 0, SEEK_SET);
  while(file_cur < file_length)
    {
      fread(&byte_data, 1, 1, fp);
      if (byte_data == 0)
	{
	  word[word_pos] = '\0';
	  if (strlen(word) > 0)
	    {
	      printf("%s\n", word);
	      file_cur += 8;
	      fseek(fp, 8, SEEK_CUR);
	    }
	  else
	    {
	      file_cur++;
	    }
	  word[0] = 0;
	  word_pos = 0;
	}
      else
	{
	  word[word_pos] = byte_data;
	  word_pos++;
	  file_cur++;
	}
    }
  fclose(fp);
  return 0;
}
