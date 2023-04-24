import csv

# 选取需要的列
selected_columns = [0, 2, 4]  # 假设需要选取第1、3、5列

# 定义 CSV 的分隔符
csv.register_dialect('semicolon', delimiter=';')

# 打开输入文件
with open('input.csv', 'r', newline='') as infile:
    reader = csv.reader(infile, dialect='semicolon')
    # 打开输出文件
    with open('output.csv', 'w', newline='') as outfile:
        writer = csv.writer(outfile)
        # 遍历每一行数据
        for row in reader:
            # 选取需要的列
            selected_row = [row[i] for i in selected_columns]
            # 写入输出文件
            writer.writerow(selected_row)