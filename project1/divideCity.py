import pandas as pd

# 读取 CSV 文件
df = pd.read_csv('in.csv')

# 提取国家名称并添加到 "PostingCountry" 列中
df['PostingCountry'] = df['PostingCity'].str.extract(r',\s*(\w+\s*\w*)$')

# 删除分离出来的值
df['PostingCity'] = df['PostingCity'].str.replace(r',\s*\w+\s*$', '', regex=True)

# 将 DataFrame 保存为 CSV 文件
df.to_csv('new_in.csv', index=False)