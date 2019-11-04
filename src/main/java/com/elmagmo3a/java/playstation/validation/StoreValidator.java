package com.elmagmo3a.java.playstation.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.elmagmo3a.java.playstation.entity.Store;
import com.elmagmo3a.java.playstation.entity.Store.Status;
import com.elmagmo3a.java.playstation.util.MessageKey;
import com.elmagmo3a.java.playstation.util.MessageResolver;

public interface StoreValidator {

	public static void checkActiveStoreExistence(Store store) {

		if (store == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					MessageResolver.resourceBundle(MessageKey.VALIDATION_OBJECT_NOT_FOUND, "Store"));
		}

		// Action is not authorized for an archived store
		if (store.getArchived()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					MessageResolver.resourceBundle(MessageKey.VALIDATION_OBJECT_ARCHIVED, "Store"));
		}

		// Action is not authorized for an archived store's company
		if (!Status.ACTIVE.equals(store.getStatus())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					MessageResolver.resourceBundle(MessageKey.VALIDATION_OBJECT_INACTIVE, "Store"));
		}

	}

}
