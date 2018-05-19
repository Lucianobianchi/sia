import csv

def read_items(name):
    with open(f'Items/{name}.tsv') as fd:
        rd = csv.reader(fd, delimiter = '\t')
        return [{
            'str': float(row[1]),
            'agi': float(row[2]),
            'dex': float(row[3]),
            'res': float(row[4]),
            'vit': float(row[5])
        } for i, row in enumerate(rd) if i > 0]
