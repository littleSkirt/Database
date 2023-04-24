import csv
import os

current_dir = os.getcwd()
print("当前文件夹是：", current_dir)

# 读取csv文件并存储数据
data = []
with open('First_Reply.csv', 'r') as f:
    reader = csv.reader(f)
    for row in reader:
        data.append(row)

# 定义一个set类型的变量，用于存储已经出现过的数据行
unique_rows = set()

# 遍历每一行数据，如果该行数据没有出现过，则添加到unique_rows中，并将该行数据写入新的csv文件中
with open('First_Reply.csv', 'w', newline='') as f:
    writer = csv.writer(f)
    for row in data:
        if tuple(row) not in unique_rows:
            writer.writerow(row)
            unique_rows.add(tuple(row))

print("Done.")
