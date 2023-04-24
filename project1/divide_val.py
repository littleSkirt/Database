import csv

input_file = 'input.csv'
output_file = 'output.csv'

# 打开输入文件和输出文件
with open(input_file, 'r') as infile, open(output_file, 'w', newline='') as outfile:
    reader = csv.reader(infile)
    writer = csv.writer(outfile)
    # 遍历每一行数据
    for row in reader:
        # 获取第3列的值，并以逗号为分隔符拆分成多个值
        values = row[2].split(',')
        # 如果拆分后有多个值，则将每个值单独写入一行
        if len(values) > 1:
            for value in values:
                # 将第3列的值替换为当前拆分的值，然后写入输出文件
                row[2] = value
                writer.writerow(row)
        else:
            # 如果只有一个值，则直接写入输出文件
            writer.writerow(row)