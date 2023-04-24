import pandas as pd

# 读取csv文件
df = pd.read_csv('in.csv')

# 去除重复行
df.drop_duplicates(inplace=True)

# 将去重后的数据保存到新的文件中
df.to_csv('out', index=False)