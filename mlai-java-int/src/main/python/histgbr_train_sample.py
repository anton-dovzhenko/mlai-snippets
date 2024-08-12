import pickle

from sklearn.datasets import fetch_california_housing
from sklearn.ensemble import HistGradientBoostingRegressor
from sklearn.metrics import mean_absolute_error
from sklearn.model_selection import train_test_split

from sklearn2pmml import sklearn2pmml

if __name__ == "__main__":
    data, target = fetch_california_housing(return_X_y=True, as_frame=True)
    X_train, X_test, y_train, y_test = train_test_split(data, target, test_size=0.25, random_state=0)
    model = HistGradientBoostingRegressor()
    model.fit(X_train, y_train)

    y_pred = model.predict(X_test)
    print("MAE =", mean_absolute_error(y_test, y_pred))

    sklearn2pmml(model, "../resources/cal_house_hgbr.pmml", with_repr=True)
    with open('../resources/cal_house_hgbr.pkl', 'wb') as f:
        pickle.dump(model, f)

    data['predicted'] = model.predict(data)
    data['target'] = target
    data.to_csv('../resources/cal_house.csv', sep='\t')

    print('Models saved')