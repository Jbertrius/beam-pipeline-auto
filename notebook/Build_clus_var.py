
# Build the dataset "cluster de variante"
varinate_df = pd.read_csv('clustering/clustered_datacar_all.csv').drop(columns='cluster')

varinate_df['cluster'] =  varinate_df['chassis'].fillna('NAN')

_fdict = pd.read_csv('dict_file.csv')
tuples = [tuple(x) for x in _fdict.values]
tuples = [{'col':'cluster', 'mapping':tuples}]

enc = category_encoders.OrdinalEncoder(cols=['cluster'], mapping=tuples)

clus_variante = enc.fit_transform(varinate_df)

clus_variante.to_csv('chassis/cluster_de_variante.csv', index=False)