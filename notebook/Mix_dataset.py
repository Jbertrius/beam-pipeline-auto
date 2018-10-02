# Melanger les données ESP, SE, FR
import pandas as pd

df_vin = pd.read_csv('chassis/df_vin_new.csv')



df_suede = pd.read_csv('testSE.csv')
df_suede['resultat'] = df_suede['resultat'].str.replace(r'.([0-9]*).',r'\1')
df_suede = df_suede.rename(index=str, columns={"resultat": "idvariante"})
df_suede = VinTransformer(define_column).transform(df_suede)

df_esp = pd.read_csv('testESP.csv')
df_esp['resultat'] = df_esp['resultat'].str.replace(r'.([0-9]*).',r'\1')
df_esp = df_esp.rename(index=str, columns={"resultat": "idvariante"})
df_esp = VinTransformer(define_column).transform(df_esp)

df_test = pd.concat([df_suede, df_esp], axis=0)
df_test['idvariante'] = df_test['idvariante'].astype('int64')
df_test['class'] = df_test['idvariante']
df_test['class'] = df_test['class'].replace(to_replace=dict(zip(clus['id'],clus['cluster'])))
df_test = df_test[ df_test['class'] < 36 ]
df_test = df_test.sample(frac=1).reset_index(drop=True)
df_test = df_test[df_test.columns[[0,2,3,4,5,1,6,7,8,9,10]]]
df_mixed = pd.concat([df_test,df_vin])
df_mixed = df_mixed[ ~df_mixed['seq'].str.contains(r'[OIQ]', regex=True, na=False) ]
df_mixed['seq'] = df_mixed['seq'].astype('int64')

df_mixed.to_csv('chassis/df_mixed.csv', index=False)