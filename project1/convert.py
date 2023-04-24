import csv    #替换csv文件中的分隔符

# 设置输入文件和输出文件
input_file = 'previous\cLike_Post.csv'
output_file = 'data\Like_Post.csv'

# 设置输入文件的分隔符和输出文件的分隔符
input_delimiter = ','
output_delimiter = ';'

# 打开输入文件
with open(input_file, 'r') as f_input:
    # 读取CSV文件
    csv_reader = csv.reader(f_input, delimiter=input_delimiter)
    # 打开输出文件
    with open(output_file, 'w', newline='') as f_output:
        # 写入CSV文件
        csv_writer = csv.writer(f_output, delimiter=output_delimiter)
        # 遍历输入文件的每一行，并写入输出文件
        for row in csv_reader:
            csv_writer.writerow(row)
            